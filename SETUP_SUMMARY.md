# FoodMind MVP Setup Summary

## ✅ What’s done

### Data & Storage
- **Room database** with entities:
  - `CategoryEntity`
  - `ProductEntity`
  - `NutritionEntity`
  - `PriceQuoteEntity`
- DAOs + repository implementations
- Local cache is the primary read source

### Import pipeline
- **Open Food Facts** importer (`data-import` module)
- WorkManager job for background import
- Dedup by **barcode** or **name+brand** hash
- Normalized nutrition per **100g / 100ml**

### Supabase integration
- REST upsert via PostgREST
- Env keys wired via `local.properties`
- Syncs categories, products, nutrition, price quotes

### Region & Pricing
- Region selection screen (auto coarse location or manual)
- Region saved locally
- Mock regional prices (clearly labeled as mock)
- PriceQuote entity with `regionKey`, `currency`, `source`, `updatedAt`

### UI (Compose)
- Region selection screen
- Catalog with search + category filters
- Product list with image/brand/category
- Product detail with nutrition + price block

---

## ⚠️ What’s still missing / next steps
- Replace **mock prices** with a real price data source
- Add **barcode scanner**
- Improve category hierarchy (parent/child)
- Add analytics / user tracking

---

## 🧪 How to run

1. Configure `local.properties`:
```
SUPABASE_URL=https://YOUR_PROJECT.supabase.co
SUPABASE_ANON_KEY=YOUR_ANON_KEY
USE_MOCK_PRICES=true
```

2. Build:
```
./gradlew assembleDebug
```

3. Run from Android Studio.

---

## 📌 Notes
- If Supabase keys are missing, app runs **local-only**.
- Import triggers after region selection.
- Images are loaded via **Coil** from Open Food Facts URLs.
