//Expected to be installed via `npm run install`
const lnk = require('./.tmp/node_modules/lnk');

const path = process.argv[2];

if(!path){
    console.error("Path not provided");
}
else {
    console.log("Linking: " + path);
}

lnk([path], '.')
    .then(() => console.log('Linked'));