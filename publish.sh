#!/bin/sh

export JEKYLL_ENV=production 
grunt check && grunt build && s3_website push
