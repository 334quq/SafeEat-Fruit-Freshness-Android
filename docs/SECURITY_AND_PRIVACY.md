# Security and Privacy Notes

This public-ready repository has been cleaned before GitHub upload.

## Removed or redacted

- `app/google-services.json` was removed and replaced with `app/google-services.example.json`.
- The Roboflow API key inside the original Colab PDF was redacted.
- The private Google Colab URL/footer in the PDF was redacted.
- The Google Drive file ID and personal Drive path shown in the PDF were redacted.
- The hard-coded model-server IP was replaced with `BuildConfig.MODEL_API_BASE_URL` and a safe placeholder in `app/build.gradle.kts`.
- Network security config no longer whitelists the original HTTP server IP.

## Before making the GitHub repository public

1. Do not commit your real Firebase `google-services.json`.
2. Do not commit raw Colab exports that contain API keys, Drive IDs, or private URLs.
3. Restrict Firebase/API keys in their respective cloud dashboards.
4. Prefer HTTPS for the prediction API endpoint.
5. If you deploy a backend, document the endpoint as configurable rather than hard-coding it.
