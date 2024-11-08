# Use the official NGINX image as the base image
FROM nginx:alpine

# Set a working directory for your app
WORKDIR /usr/share/nginx/html

# Copy the built application files (e.g., index.html, CSS, JS) to the NGINX default directory
COPY ./dist /usr/share/nginx/html

# Expose port 80 to serve the app
EXPOSE 80

# Copy custom NGINX configuration file (if you need any specific configs)
# COPY nginx.conf /etc/nginx/nginx.conf

# Start NGINX
CMD ["nginx", "-g", "daemon off;"]