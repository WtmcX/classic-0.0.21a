
# Minecraft c0.0.21a TeaVM Port

This project is a port of **Minecraft c0.0.21a** to the browser using [TeaVM](https://teavm.org/), allowing users to experience this early version of Minecraft directly from a web browser.

## Features

- Full functionality of Minecraft c0.0.21a in a browser environment.
- Smooth integration using TeaVM for Java-to-JavaScript transpilation.
- No plugins or additional installations required—just open the web page and play!

## Requirements

- A modern web browser with JavaScript enabled.
- TeaVM for building the project (already configured in the repository).

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/WtmcX/classic-0.0.21a.git
   cd classic-0.0.21a
   ```

2. Open the project using Eclipse IDE:
   - Open eclipse ide
   - Open a folder / create
   - Open it on eclipse
   - Click import project
   - Click general and Exsisting Java Project
   - Select the folder 'desktopRuntime/EclipseProject'

3. Run the desktop runtime:
   - RClick eaglerDebugRuntime.launch and click Run as and click the first one.

## Development

Still going...

### Prerequisites

- Java Development Kit (JDK) 11 or later
- Gradle build tool
- TeaVM (already included as a dependency)

### Building the Project

To rebuild the project and generate the web files:
```bash
./gradlew generateJavaScript
```
And open MakeOfflineDownload.bat
### Directory Structure

- `src/game/java`: Source code for Minecraft c0.0.21a.
- `javascript`: Output directory containing the transpiled JavaScript and web resources.

## Credits

- Original Minecraft code by **Mojang**.
- Ported to the browser using **TeaVM** by [EymenWSMC].

## License

This project is for educational and personal use only. Minecraft and all related assets are owned by **Mojang Studios**. This port does not claim ownership or rights to the original game.

---
Enjoy playing Minecraft c0.0.21a in your browser!
```
