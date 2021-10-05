FROM node:14.18.0
WORKDIR /home/app
COPY package.json ./
COPY package-lock.json ./
COPY . ./
RUN npm i
CMD ["npm", "run", "start"]
