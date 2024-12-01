# Project Name

## Introduction

This project is a [Expo](https://expo.dev/) React Native application designed to run on multiple platforms, including web browsers.

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed on your system:

- [Node.js](https://nodejs.org/) (version >= 12.x)
- [Expo CLI](https://docs.expo.dev/get-started/installation/)
- [Git](https://git-scm.com/)

### Installation

1. Clone the repository to your local machine:

   ```bash
   git clone https://git.uwaterloo.ca/rapideye360/rapideye-web.git
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

### Running the Application

#### For Development

To run the application locally in development mode, follow these steps:

1. Start the Expo development server:

   ```bash
   expo start
   ```

2. Follow the on-screen instructions to open the app in an Expo client on your mobile device, or press `w` in the terminal to open the app in a web browser.

 ```bash
   npm run web
   ```

#### For Web Deployment

To deploy the application to the web, follow these steps:

1. Build the web version of the app:

   ```bash
   expo build:web
   ```

2. Deploy the contents of the `web-build` directory to your preferred hosting service.

### Configuration

No additional configuration is required to run the application on the web. However, you may customize the behavior for different platforms using [Expo configuration](https://docs.expo.dev/workflow/configuration/).

### Troubleshooting

If you encounter any issues during installation or running the application, refer to the [Expo documentation](https://docs.expo.dev/) or search for solutions on [Stack Overflow](https://stackoverflow.com/).

## Contributing

Contributions are welcome! If you find any bugs or have suggestions for improvement, please [open an issue](https://github.com/your-username/project-name/issues) or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).
