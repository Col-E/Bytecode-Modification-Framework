git add .
echo 'Enter the commit message:'
read commitMsg
git commit -m "$commitMsg"
git pull origin master
git push origin master
read