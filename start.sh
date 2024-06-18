docker-compose down

docker build -t travel-app:latest ./TravelApp

docker build -t travel-app-frontend:latest ./travelapp-frontend

docker-compose up --build --force-recreate --remove-orphans


