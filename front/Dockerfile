# Use Node.js image to build the React app
FROM node:16-alpine AS build
WORKDIR /app

# Copy package.json and install dependencies
COPY package.json ./
RUN npm install

# Copy the rest of the application code and build it
COPY . ./
RUN npm run build

# Use Nginx to serve the built application
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html

# Expose port 80
EXPOSE 80
