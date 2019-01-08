FROM maven:3.6 AS matlab-runtime

LABEL description="Docker image of angio backend"

WORKDIR /angio-backend

RUN curl http://ssd.mathworks.com/supportfiles/downloads/R2017a/deployment_files/R2017a/installers/glnxa64/MCR_R2017a_glnxa64_installer.zip -O --progress-bar && \
    unzip -q -d /mrc_installer MCR_R2017a_glnxa64_installer.zip && \
    /mrc_installer/install -mode silent -agreeToLicense yes -destinationFolder /matlabruntime && \
    rm -rf /mrc_installer

ENV XAPPLRESDIR=/matlabruntime/v92/X11/app-defaults
ENV LD_LIBRARY_PATH=/matlabruntime/v92/runtime/glnxa64:/matlabruntime/v92/bin/glnxa64:/matlabruntime/v92/sys/os/glnxa64:/matlabruntime/v92/sys/opengl/lib/glnxa64

FROM matlab-runtime AS angio-backend

COPY . .

ENTRYPOINT ["mvn", "clean", "spring-boot:run"]
