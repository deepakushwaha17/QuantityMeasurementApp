# ──────────────────────────────────────────────────────────────
# QuantityMeasurementApp — Makefile
# ──────────────────────────────────────────────────────────────
COMPOSE      = docker compose
COMPOSE_DEV  = $(COMPOSE) -f docker-compose.yml -f docker-compose.dev.yml

.PHONY: help setup build up down restart logs ps clean nuke

help: ## Show this help message
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | \
	  awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[36m%-18s\033[0m %s\n", $$1, $$2}'

setup: ## Copy .env.example → .env (run once)
	@if [ ! -f .env ]; then cp .env.example .env && echo "✅  .env created — fill in your secrets!"; \
	else echo "⚠️  .env already exists, skipping."; fi

build: ## Build all Docker images (no cache)
	$(COMPOSE) build --no-cache

up: ## Start all services (production mode)
	$(COMPOSE) up -d

up-dev: ## Start all services (development mode, verbose logs)
	$(COMPOSE_DEV) up

down: ## Stop and remove containers
	$(COMPOSE) down

restart: ## Restart all services
	$(COMPOSE) restart

logs: ## Tail logs for all services
	$(COMPOSE) logs -f

logs-%: ## Tail logs for a specific service  e.g. make logs-api-gateway
	$(COMPOSE) logs -f $*

ps: ## Show running containers
	$(COMPOSE) ps

clean: ## Remove containers + named volumes (keeps images)
	$(COMPOSE) down -v

nuke: ## Remove everything — containers, volumes, images
	$(COMPOSE) down -v --rmi all --remove-orphans

mvn-build: ## Build all Maven modules (skipping tests)
	./mvnw clean package -DskipTests
