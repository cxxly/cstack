# Copyright (2017, ) Institute of Software, Chinese academy of Sciences
# Author wuheng@otcaix.iscas.ac.cn
# Date   2017/3/22

for uuid in `docker images | awk '{print$3}' | awk 'NR>1' | uniq`
do
    name=$(docker images | grep $uuid | awk $'{print$1}' | head -1)
    vers=$(docker images | grep $uuid | awk $'{print$2}' | head -1)
    echo "save image "$name:$vers
    docker save $name:$vers > ${name//\//_}_$vers.tar
done
