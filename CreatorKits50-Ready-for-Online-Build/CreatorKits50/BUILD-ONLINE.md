# Build online without installing Maven

1. Create a free GitHub account and a new **public** repository named `CreatorKits50`.
2. Extract this ZIP on your computer.
3. On the new repository page, click **Add file → Upload files**.
4. Upload the *contents* of the `CreatorKits50` folder (including the hidden `.github` folder). If Windows doesn't show `.github`, turn on **View → Show → Hidden items** in File Explorer.
5. Click **Commit changes**. GitHub Actions should start automatically.
6. Open the repository's **Actions** tab and select **Build CreatorKits50**. When it is green, open that run and download **CreatorKits50-JAR** under Artifacts. Extract that artifact ZIP to get the `.jar`.

Note: Aternos generally only installs plugins available in its software/addon catalog; it does not provide normal arbitrary JAR upload. If your custom plugin cannot be installed there, use a host that supports custom plugin uploads (for example exaroton) or ask Aternos support about supported options.
