# Copyright (2017, ) Institute of Software, Chinese academy of Sciences
# Author wuheng@otcaix.iscas.ac.cn
# Date   2017/3/22

for name in `ls | grep tar`
do
    echo "load image "$name
    docker load < $name
done
