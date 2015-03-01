ps -ef | grep "/clump-zoot-sample/tracks/target/" | sed -n 1p | awk -F ' ' '{print $2}' | xargs kill -9
ps -ef | grep "/clump-zoot-sample/users/target/" | sed -n 1p | awk -F ' ' '{print $2}' | xargs kill -9
ps -ef | grep "/clump-zoot-sample/presentation/target/" | sed -n 1p | awk -F ' ' '{print $2}' | xargs kill -9