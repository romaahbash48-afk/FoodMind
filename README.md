# FoodMind

FoodMind is a Kotlin + Jetpack Compose Android app that imports **real product data** from
[Open Food Facts](https://world.openfoodfacts.org/) and shows nutrition, images, and regional prices.
Local storage is **Room**, remote master is **Supabase** (optional).

## ✅ MVP features (current)
- Open Food Facts import by category (real products + images + nutriments)
- Region selection (auto by coarse location or manual)
- Product catalog with **search + category filters**
- Product details: nutrition per 100g/ml + image
- Regional price block with **"approximate (mock)"** label
- WorkManager background import pipeline
- Room cache + Supabase sync (when keys provided)

> Prices are mocked for now (clearly labeled) until a real price data source is added.

---

## Data sources
- **Open Food Facts** (products, nutrition, images, barcodes)
- **Supabase** as master DB (optional, configured via `local.properties`)

---

## Getting started

### Prerequisites
- Android Studio Jellyfish or newer
- JDK 17
- Android SDK 36

### Configure Supabase (optional but recommended)
Create `local.properties` in the project root (do **not** commit):
```
SUPABASE_URL=https://YOUR_PROJECT.supabase.co
SUPABASE_ANON_KEY=YOUR_ANON_KEY
USE_MOCK_PRICES=true
```

If `SUPABASE_*` keys are missing, the app runs **local-only** using Room.

### Build & run
```
./gradlew assembleDebug
```
Then run from Android Studio or:
```
./gradlew installDebug
```

---

## Import pipeline (Open Food Facts)
On first region selection, a WorkManager job:
1. Downloads categories (dairy, cheese, grains, meat, seafood, vegetables, fruits, drinks, snacks, bakery)
2. Loads ~50 products per category (≈ 500 total)
3. Normalizes nutrition to **per 100g / 100ml**
4. Stores to **Room**, and upserts to **Supabase** if configured
5. Seeds **mock prices** per region (labeled as mock)

---

## Project structure

```
app/
  data/
    importer/      # import coordinator + WorkManager
    local/         # Room entities + DAOs
    remote/        # Supabase REST sync
    pricing/       # mock price seeder
  domain/
    model/         # Product, Nutrition, PriceQuote, Category, Region
    repository/    # repository interfaces
    usecase/       # use cases
  presentation/
    screens/
      home/        # catalog + search + filters
      detail/      # product detail screen
      region/      # region selection
  navigation/      # Navigation Compose graph
  di/              # Hilt modules

data-import/
  Open Food Facts client + importer
```

---

## Commands
```
# Unit tests
./gradlew test
```

---

## Next steps
- Plug a **real price source** (replace mock prices)
- Add **barcode scanning**
- Add **offline-first sync** / conflict resolution
- Improve **category taxonomy** (parent/child)

---

## License
MIT
