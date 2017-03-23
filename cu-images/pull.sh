# Copyright (2017, ) Institute of Software, Chinese academy of Sciences
# Author wuheng@otcaix.iscas.ac.cn
# Date   2017/3/22

while read image
do
    echo "docker pull "$image
    docker pull $image
done < images
