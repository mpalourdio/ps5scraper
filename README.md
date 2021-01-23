## WTF ?

- Crawls websites to check PS5 availability. See this [Enum](src/main/java/com/mpalourdio/projects/ps5scraper/services/Crawlables.java)
- Sends an email if the PS5 is possibly there

## HOWTO

- First, you need JDK11+
- Add a file named `scraper.properties` in the `properties` directory (check the example file for values you need to fill)
- `./mvnw clean package` to build the final executable
- `cd target && ./ps5scraper.jar`

## That does not work

No support given, sorry. Take the code and make it work. Life is too short :). I was just fed-up to `F5` websites...

## WARNING

Do NOT make a too much agressive cron value, or you'll be banned by websites VERY fast
