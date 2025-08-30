# AI Chat Android App

This repository contains the source code for a simple Android chat application that connects to a Hugging Face AI model. The entire build and release process is automated using GitHub Actions.

## How It Works
- You write your code or make changes.
- You push your changes to the `main` branch.
- GitHub Actions automatically builds the app to make sure there are no errors.
- When you are ready to create a new version of the app, you create a "tag" (e.g., `v1.0`).
- When you push a new tag, GitHub Actions builds the final installable APK file and creates a new Release on the GitHub repository's "Releases" page.

---

## 🚀 Getting Started (One-Time Setup)

To use this application, you need a free API key from Hugging Face. Follow these steps exactly.

### Step 1: Get Your Hugging Face API Token
1. Go to the Hugging Face website: [https://huggingface.co/](https://huggingface.co/)
2. Sign up for a free account if you don't have one.
3. Once logged in, click on your profile picture in the top-right corner.
4. Go to **Settings** -> **Access Tokens**.
5. Click on the **"New token"** button.
6. Give your token a name (e.g., "Android App") and set the role to **"Read"**.
7. Click **"Generate a token"**.
8. **Copy the token immediately!** It will look something like `hf_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`. This is your secret API key.

### Step 2: Add the Token to Your GitHub Repository
The automated build process needs access to this token, but it must be kept secret.
1. In this GitHub repository, go to the **Settings** tab.
2. In the left menu, navigate to **Secrets and variables** -> **Actions**.
3. Click the **"New repository secret"** button.
4. For the **Name**, you MUST enter exactly: `HF_API_TOKEN`
5. For the **Secret**, paste the token you copied from Hugging Face.
6. Click **"Add secret"**.

That's it! The setup is complete.

---

## 📦 How to Get Your APK

1. Make sure you have completed the one-time setup above.
2. Go to the "Code" page of your repository.
3. Click on **"Releases"** on the right-hand side.
4. You will see a list of releases (e.g., `v1.0`). Click on the latest one.
5. Under the **Assets** section, you will find the `.apk` file (e.g., `app-debug.apk`).
6. You can download this file and install it on an Android device.

## 🔧 For Developers (Changing the Model)

You can change the AI model by editing the `API_URL` constant in the `app/src/main/java/com/example/aichat/MainActivity.kt` file.
