1. Create S3 bucket
2. Add group and user in AWS IAM (Security Credentials top right menu)
3. Set up login and password for the new user
4. Add permissions to the group (IAM > Groups > Permissions):

```
{
    "Statement": [
        {
            "Action": "s3:*",
            "Effect": "Allow",
            "Resource": [
                "arn:aws:s3:::www.michaelszymczak.com",
                "arn:aws:s3:::www.michaelszymczak.com/*"
            ]
        },
        {
            "Effect": "Allow",
            "Action": "s3:ListAllMyBuckets",
            "Resource": "arn:aws:s3:::*"
        }
    ]
}
```
5. Log in using the create user and go to S3 to see the bucket. You should be able to upload files etc.
