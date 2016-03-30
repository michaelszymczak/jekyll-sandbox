#!/bin/sh

grunt check && grunt build && s3_website push
