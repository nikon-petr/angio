FROM java:openjdk-8-jdk AS matlab-runtime

LABEL description="Docker image of angio analyse executor"

RUN curl http://ssd.mathworks.com/supportfiles/downloads/R2017a/deployment_files/R2017a/installers/glnxa64/MCR_R2017a_glnxa64_installer.zip -O --progress-bar && \
    unzip -q -d /mrc_installer MCR_R2017a_glnxa64_installer.zip && \
    /mrc_installer/install -mode silent -agreeToLicense yes -destinationFolder /matlabruntime && \
    rm -rf /mrc_installer

ENV XAPPLRESDIR=/matlabruntime/v92/X11/app-defaults
ENV LD_LIBRARY_PATH=/matlabruntime/v92/runtime/glnxa64:/matlabruntime/v92/bin/glnxa64:/matlabruntime/v92/sys/os/glnxa64:/matlabruntime/v92/sys/opengl/lib/glnxa64

FROM matlab-runtime AS analyse-executor

VOLUME /root/.m2/ /root/.m2/

COPY . /angio

WORKDIR /angio

RUN ./mvnw verify clean --fail-never

RUN ./mvnw clean package -pl analyse-executor -am spring-boot:repackage

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "analyse-executor/target/analyse-executor-0.0.1-SNAPSHOT.jar"]
