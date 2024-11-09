# JavaScript web application, containerizing it with Docker, and setting up a CI/CD pipeline using Jenkins.

![image](https://github.com/user-attachments/assets/f1b954cc-eef8-498a-a89f-89f7ae6e3db4)



Here are the step-by-step details to set up an end-to-end Jenkins pipeline for a Javascript web application ontainerizing it with Docker, and setting up a CI/CD pipeline using Jenkins. The application is served by NGINX and is designed to be deployed to AWS ECR and Kubernetes. Below is a breakdown of each component and its purpose.

**Project Structure :** 

.
├── dist

│   ├── app.js

│   ├── index.html

│   └── styles.css

├── jenkins

│   ├── build_and_deploy.groovy

│   └── Dockerfile


**Prerequisites:**

   **-  AWS:** AWS ECR repository, credentials, and proper IAM permissions.
   **-  Kubernetes:** A running Kubernetes cluster to deploy the application
   **-  Jenkins:** Proper setup with Docker and Kubernetes plugins installed.
   

**dist Folder**

The dist folder contains the application files:

- app.js - A JavaScript file with a simple script to display a message in the web app.
- index.html - The main HTML file that loads the JavaScript and displays the content.
- styles.css - A CSS file that styles the HTML page.

**jenkins Folder**

The jenkins folder contains:

- build_and_deploy.groovy - A Jenkins pipeline script that automates the CI/CD pipeline steps, from building the Docker image to deploying it to Kubernetes.

- Dockerfile - Defines the Docker image that will host the application using NGINX.


**Detailed Explanation**


**1. Application Files in dist Folder**
   
**- app.js**

  A basic JavaScript file that waits until the DOM is loaded and then updates the content of the div with the ID "content."
  This provides a basic interaction by changing text content to indicate the app is live.

**- index.html**

A simple HTML page that includes a title, heading, and a <div> where the JavaScript application text will display.
Loads app.js and styles.css for interactivity and styling.

**- styles.css**

Provides a minimal style for the page, centering the content and adding colors for readability.

**2. Dockerfile in jenkins Folder**

The Dockerfile defines the environment and setup for the application:

**Base Image:** Uses the nginx:alpine image, which is a lightweight NGINX container.

**Working Directory:** Sets the working directory to /usr/share/nginx/html, which is where NGINX serves content.

**Copy Files:** Copies all files from dist to the working directory.

**Expose Port 80:** Opens port 80 to allow external access to the application.

**Start Command:** Starts the NGINX server.


**3. Jenkins Pipeline Script** - build_and_deploy.groovy

The Jenkins pipeline automates the following steps:

**Environment Variables:** Sets AWS region, ECR repository name, and Docker image tag.


**Stages:**

**Checkout:** Clones the code repository.

**Build:** Builds the Docker image using the Dockerfile.

**Authenticate with ECR:** Logs in to AWS ECR to allow image pushing.

**Push Image:** Pushes the Docker image to the ECR repository.

**Deploy:** Deploys the new image to Kubernetes by updating the existing deployment.



**Post Steps:**

**Always:** Cleans up Docker images and workspace after each build.

**Success:** Outputs a success message.

**Failure:** Outputs a failure message.


**How to Run**


**Set Up Jenkins:**

Ensure Jenkins has AWS credentials and Docker installed to run the pipeline.

**Build and Push the Docker Image:**

The pipeline will build the Docker image, log in to ECR, and push the image.

**Deploy to Kubernetes:**

The pipeline updates the Kubernetes deployment with the latest image version


  
