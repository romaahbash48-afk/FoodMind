# Compliance Gate — German Retailers

This project **does not crawl retailer sites** unless explicitly permitted by
robots.txt *and* Terms of Service. If any check is unclear or negative, the
connector returns **NOT_ALLOWED** and performs **no requests**.

## Summary table

| retailer | domain | robots allows catalog/search? | ToS allows automated collection? | decision |
|---|---|---|---|---|
| EDEKA | https://www.edeka.de | **No** — `Disallow: /lieferservice/` and search paths | **Unclear** — only Impressum/Datenschutz found, no explicit permission | **DENY** |
| REWE | https://www.rewe.de | **No** — `Disallow: /shop/` and search query params | **Unclear/Blocked** — Nutzungsbedingungen blocked by anti-bot | **DENY** |
| ALDI Süd | https://www.aldi-sued.de | **Partial** — search query `?q=` disallowed, product sitemaps allowed | **Unclear** — Impressum only, no explicit scraping permission | **NEED_PARTNERSHIP** |
| ALDI Nord | https://www.aldi-nord.de | **No** — `Disallow: /suchergebnisse` | **Unclear** — Impressum only, no explicit scraping permission | **NEED_PARTNERSHIP** |

## Evidence links

### EDEKA
- robots.txt: https://www.edeka.de/robots.txt  
- Impressum: https://www.edeka.de/impressum/

### REWE
- robots.txt: https://www.rewe.de/robots.txt  
- Nutzungsbedingungen (blocked by anti-bot): https://www.rewe.de/service/nutzungsbedingungen/  
- Impressum: https://www.rewe.de/service/impressum/

### ALDI Süd
- robots.txt: https://www.aldi-sued.de/robots.txt  
- Impressum: https://www.aldi-sued.de/de/unternehmen/impressum.html

### ALDI Nord
- robots.txt: https://www.aldi-nord.de/robots.txt  
- Impressum: https://www.aldi-nord.de/impressum.html

---

**Policy:** If `decision != ALLOW`, the connector **must not** request the retailer site.
# Compliance Gate (Germany Retailers)

This document records the compliance check for retailer domains.  
We only implement connectors when **robots.txt allows catalog/search access** *and*
**Terms/Nutzungsbedingungen explicitly allow automated collection**.

| retailer | domain | robots allows catalog/search? | ToS allows automated collection? | decision |
| --- | --- | --- | --- | --- |
| EDEKA | https://www.edeka.de | **No** (search paths restricted; no explicit catalog allowance) | **Unclear** (Impressum only, no explicit scraping permission found) | **NEED_PARTNERSHIP** |
| REWE | https://www.rewe.de | **No** (`search` params + `/shop` disallowed) | **Unclear** (Nutzungsbedingungen page blocked by anti-bot; no automated access) | **DENY** |
| ALDI Süd | https://www.aldi-sued.de | **No** (search query disallowed; product sitemap exists) | **Unclear** (Impressum available, no explicit scraping permission found) | **NEED_PARTNERSHIP** |
| ALDI Nord | https://www.aldi-nord.de | **No** (`/suchergebnisse` disallowed) | **Unclear** (Impressum available, no explicit scraping permission found) | **NEED_PARTNERSHIP** |

## Sources checked (robots + legal links)
- EDEKA robots: https://www.edeka.de/robots.txt  
  Impressum: https://www.edeka.de/impressum/
- REWE robots: https://www.rewe.de/robots.txt  
  Nutzungsbedingungen (blocked by anti-bot): https://www.rewe.de/service/nutzungsbedingungen/  
  Impressum: https://www.rewe.de/service/impressum/
- ALDI Süd robots: https://www.aldi-sued.de/robots.txt  
  Impressum: https://www.aldi-sued.de/de/unternehmen/impressum.html
- ALDI Nord robots: https://www.aldi-nord.de/robots.txt  
  Impressum: https://www.aldi-nord.de/impressum.html

## Policy
If **decision != ALLOW**, the connector **must not** make requests and must return `NOT_ALLOWED`.
## Compliance Gate — German Retailers (robots.txt + ToS)

> We do **not** implement connectors unless both robots.txt and Terms permit automated access.

| retailer | domain | robots allows catalog/search? | ToS allows automated collection? | decision |
| --- | --- | --- | --- | --- |
| EDEKA | https://www.edeka.de | **Partial** (general crawl allowed, search routes disallowed) | **Unclear** (no explicit scraping allowance found; refer to Impressum) | NEED_PARTNERSHIP |
| REWE | https://www.rewe.de | **No** (search/shop query patterns disallowed) | **Unclear** (Terms page blocked by anti‑bot challenge) | DENY |
| ALDI Süd | https://www.aldi-sued.de | **Partial** (product sitemaps allowed, search query disallowed) | **Unclear** (no explicit scraping allowance found; refer to Impressum) | NEED_PARTNERSHIP |
| ALDI Nord | https://www.aldi-nord.de | **Partial** (search results disallowed) | **Unclear** (no explicit scraping allowance found; refer to Impressum) | NEED_PARTNERSHIP |

### Evidence links
- EDEKA robots: https://www.edeka.de/robots.txt  
- EDEKA Impressum: https://www.edeka.de/impressum/  

- REWE robots: https://www.rewe.de/robots.txt  
- REWE Impressum: https://www.rewe.de/service/impressum/  
- REWE Terms (blocked by anti‑bot at time of check): https://www.rewe.de/service/nutzungsbedingungen/  

- ALDI Süd robots: https://www.aldi-sued.de/robots.txt  
- ALDI Süd Impressum: https://www.aldi-sued.de/de/unternehmen/impressum.html  

- ALDI Nord robots: https://www.aldi-nord.de/robots.txt  
- ALDI Nord Impressum: https://www.aldi-nord.de/impressum.html  

### Decision rule
If **robots.txt** or **Terms** do **not** clearly allow automated collection, the connector is **NOT_ALLOWED** and performs **no requests**.
