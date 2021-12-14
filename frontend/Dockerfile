FROM node:14.18.0
WORKDIR /home/app
COPY package.json ./
COPY package-lock.json ./
COPY . ./
RUN npm config set unsafe - perm true
RUN npm i
RUN mkdir -p node_modules/.cache && chmod -R 777 node_modules/.cache
CMD ["npm", "run", "start"]
