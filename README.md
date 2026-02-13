# GLBI Canada Android App

An Android application for advocacy of Guaranteed Livable Basic Income (GLBI) in Canada.

## Overview

GLBI Canada is an educational and advocacy app designed to help Canadians understand and engage with Guaranteed Livable Basic Income legislation. The app provides:

- Information about current and past parliamentary bills related to GLBI
- Tools to create and submit briefs to Parliament
- A calculator to compare different GLBI scenarios based on PBO data
- Educational resources about the GLBI framework

## Features

### 1. Parliamentary Bills
- View current and previous GLBI-related bills (S-206, C-223, S-233)
- Direct links to official parliamentary pages
- Guidance on submitting briefs to committees

### 2. Brief Submission
- Form to create structured briefs supporting GLBI
- Preview your brief before export
- Export briefs as PDF
- Share briefs via email or other apps
- All data stays on your device unless you explicitly share

### 3. Email Your MP
- Complete contact list of all Members of Parliament
- Search and filter MPs by name, riding, or province
- Email addresses and phone numbers for all MPs
- Pre-populated email templates for GLBI advocacy
- Works offline - draft emails and send when connected
- All MP data stored locally for privacy

### 4. Scenario Calculator
- Compare 4 different GLBI clawback scenarios
- Based on 2025 PBO report data (February 19, 2025)
- Base benefits: Single $21,903, Household $30,975
- Interactive income slider
- Side-by-side comparison results

### 5. About & Privacy
- Full transparency about data handling
- Version information
- Disclaimer and license information

## Build Instructions

### Prerequisites
- Android Studio Arctic Fox (2020.3.1) or later
- JDK 8 or later (JDK 21 recommended for full compatibility)
- Android SDK with API level 34
- Gradle 9.0-milestone-1 (automatically downloaded by wrapper)
- Internet connection (for initial Gradle and dependency downloads)

### Building the App

1. Clone the repository:
```bash
git clone https://github.com/jvm2020/glbi.git
cd glbi
```

2. Open the project in Android Studio:
   - File → Open → Select the `glbi` directory

3. Sync Gradle:
   - Android Studio should automatically sync. If not, click "Sync Now" in the notification bar
   - **Note**: First sync will download Android build tools and dependencies from Maven/Google repositories

4. Build the app:
   - Build → Make Project (Ctrl+F9 / Cmd+F9)

5. Run on device or emulator:
   - Run → Run 'app' (Shift+F10 / Ctrl+R)

### Running Tests

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

### Build Notes

The project uses:
- Gradle 9.0-milestone-1
- Android Gradle Plugin 8.1.4
- Compile SDK 34
- Min SDK 24

**Network Requirements**: The first build requires internet access to download:
- Gradle wrapper and build tools
- Android SDK components
- Project dependencies (AndroidX, Material Design, GSON, etc.)

After the initial setup, most development can be done offline.

## Privacy Philosophy

This app adheres to strict privacy principles:

- **No data collection**: The app does not collect, store, or transmit any personal information
- **Offline-first**: All functionality works offline
- **No analytics**: No analytics or tracking services
- **No third-party services**: No connections to external services except when explicitly opening web links
- **User control**: Users explicitly choose when to share data via standard Android intents
- **On-device only**: All inputs remain on the device unless the user exports/shares

See [PRIVACY.md](PRIVACY.md) for full details.

## Technical Details

### Architecture
- **Pattern**: MVVM (Model-View-ViewModel)
- **Language**: Java 8
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Build System**: Gradle 9.0-milestone-1 with Android Gradle Plugin 8.1.4

### Libraries & Dependencies
- AndroidX AppCompat
- Material Design Components
- Navigation Component
- Lifecycle & ViewModel
- GSON for JSON parsing

### Internationalization
- English (en)
- French (fr)

### Accessibility
- Content descriptions for all interactive elements
- Minimum contrast ratios for WCAG AA compliance
- Support for large text sizes

## Configuration

The app uses JSON configuration files:

### glbi_config.json
Located at `app/src/main/assets/config/glbi_config.json`, contains:
- Base benefit amounts (Single: $21,903, Household: $30,975)
- Clawback scenario definitions
- Parliamentary bill URLs
- Submission guidance links
- Build date and data source dates

### mp_contacts.json
Located at `app/src/main/assets/config/mp_contacts.json`, contains:
- Complete list of Members of Parliament
- Contact information (email, phone)
- Riding and province data
- Party affiliations

This allows updating policy values, links, and MP information without code changes.

## Splash Screen

The app displays a splash screen on startup showing:
- Build date
- PBO data date (benefit amounts source)
- MP data last updated date

This transparency helps users understand when the data might be outdated.

## Data Source

Base benefit amounts are from the Parliamentary Budget Officer (PBO) report published February 19, 2025:
- [Distributional analysis of a national guaranteed basic income (Update)](https://www.pbo-dpb.ca/en/publications/RP-2425-029-S--distributional-analysis-national-guaranteed-basic-income-update--analyse-distributive-un-revenu-base-garanti-echelle-nationale-mise-jour)

## Disclaimer

**GLBI.ca is not a funded organization or entity.** It is solely an advocacy project by Joseph Vander Meer and all opinions thereof are not respective of government bodies.

The calculator and information provided in this app are for educational and advocacy purposes only. They do not represent official government policy or guaranteed outcomes.

## Contributing

This is an advocacy project. If you'd like to contribute:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

Please ensure:
- Code follows existing style
- Unit tests pass
- New features include tests
- Privacy principles are maintained

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

Project maintained by Joseph Vander Meer
- GitHub: https://github.com/jvm2020/glbi

## Acknowledgments

- Parliamentary Budget Officer for providing comprehensive GLBI analysis
- Contributors to open-source libraries used in this project
- The GLBI advocacy community in Canada

---

**Note**: This app is a tool for advocacy and education. For official information about GLBI legislation, please consult official parliamentary sources linked in the app.
