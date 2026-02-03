# Compliance Gate - German Retailers

This project does not crawl retailer sites unless robots.txt allows
catalog/search access and Terms of Service explicitly allow automated access.
If any check is unclear or negative, the connector returns NOT_ALLOWED and
performs no requests.

## Summary table

| retailer  | domain | robots allows catalog/search? | ToS allows automated collection? | decision |
|---|---|---|---|---|
| EDEKA | https://www.edeka.de | No (search paths and delivery disallowed) | Unclear (Impressum/Datenschutz only) | DENY |
| REWE | https://www.rewe.de | No (shop and search params disallowed) | Unclear/Blocked (Terms page blocked by anti-bot) | DENY |
| ALDI SUED | https://www.aldi-sued.de | Partial (product sitemaps allowed, search query disallowed) | Unclear (Impressum only) | NEED_PARTNERSHIP |
| ALDI NORD | https://www.aldi-nord.de | No (search results disallowed) | Unclear (Impressum only) | NEED_PARTNERSHIP |

## Evidence links

### EDEKA
- robots.txt: https://www.edeka.de/robots.txt
- Impressum: https://www.edeka.de/impressum/

### REWE
- robots.txt: https://www.rewe.de/robots.txt
- Nutzungsbedingungen (blocked by anti-bot): https://www.rewe.de/service/nutzungsbedingungen/
- Impressum: https://www.rewe.de/service/impressum/

### ALDI SUED
- robots.txt: https://www.aldi-sued.de/robots.txt
- Impressum: https://www.aldi-sued.de/de/unternehmen/impressum.html

### ALDI NORD
- robots.txt: https://www.aldi-nord.de/robots.txt
- Impressum: https://www.aldi-nord.de/impressum.html

## Policy
If decision != ALLOW, the connector must not request the retailer site.
