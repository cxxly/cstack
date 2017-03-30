# Copyright (2017, ) Institute of Software, Chinese academy of Sciences
# Author wuheng@otcaix.iscas.ac.cn
# Date   2017/3/22

url=http://118.190.46.58/corestack/once/cloud/cStack/images/

curl $url | grep "href=" | grep ".tar" > page
while read item
do
    start=$(echo $item | awk '{print index($item,"href")}')
    end=$(echo $item | awk '{print index($item,"tar\"")}')
    name=${item:$start+7:$end-$start-3}
    echo "wget "$url$name
done < page

rm -rf page
