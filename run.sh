cd holidayCalculator
mvn clean install -DskipTests
docker build -t holiday-calculator .
docker-compose up -d
