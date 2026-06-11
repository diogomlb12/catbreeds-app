# CatBreeds

Android app built with Kotlin that uses [The Cat API](https://thecatapi.com/) to browse cat breeds, check details and save favourites.

## Architecture

Clean Architecture + MVVM, split into multiple modules:

```
app/
core/
  ├── domain/      # models, use cases, repository interfaces (pure Kotlin)
  ├── network/     # Retrofit, API, DTOs
  ├── database/    # Room, DAOs
  └── ui/          # shared Compose components and theme
feature/
  ├── breeds/      # breed list with search and paging
  ├── detail/      # breed detail
  └── favourites/  # favourites list with average lifespan
```

## Tech Stack

- **UI** — Jetpack Compose + Material 3
- **DI** — Hilt
- **Network** — Retrofit + Kotlinx Serialization
- **Images** — Coil
- **Pagination** — Paging 3 with RemoteMediator
- **Local DB** — Room
- **Async** — Coroutines + Flow
- **Testing** — JUnit4, MockK, Turbine

## Notes

**Multi-module** — dependency boundaries are enforced by Gradle. A feature module can't accidentally import Retrofit because it's simply not in its classpath.

**Offline-first** — RemoteMediator caches breeds in Room on first load. Favourite state is preserved on refresh by checking existing DB entries before upsert.

**API key** — stored in `local.properties` (gitignored) and exposed via `BuildConfig`.

**Search** — uses a separate `PagingSource` against `/breeds/search` to avoid polluting the local cache.
