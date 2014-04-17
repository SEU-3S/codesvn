# This file will be used directly by both php and perl, so it must be valid in both languages.

$max_upload = 200000000;           # Max size allowed for uploaded files
$tmp_dir="c:/upload/uploadtemp";   # Temporary directory, must be writable by both cgi-script and php scripts
$upload_dir="c:/upload";           # Where to permanently store the uploaded files, when the upload is completed
