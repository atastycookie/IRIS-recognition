#!/bin/bash
cd build/classes
java -Djava.library.path=/usr/lib/jni -cp /usr/lib/java/swt.jar:. org/cs02rm0/jirrm/SWTFrame
