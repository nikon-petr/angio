FROM maven:3.5

LABEL version="1.0"
LABEL description="Docker image with maven and matlab runtime"

RUN wget http://ssd.mathworks.com/supportfiles/downloads/R2017a/deployment_files/R2017a/installers/glnxa64/MCR_R2017a_glnxa64_installer.zip -O mrc_installer.zip
RUN unzip mrc_installer.zip -d /mrc_installer
RUN rm mrc_installer.zip

RUN /mrc_installer/install -mode silent -agreeToLicense yes -destinationFolder /matlabruntime
RUN rm -rf /mrc_installer

ENV XAPPLRESDIR=/matlabruntime/v92/X11/app-defaults
ENV LD_LIBRARY_PATH=/matlabruntime/v92/runtime/glnxa64:/matlabruntime/v92/bin/glnxa64:/matlabruntime/v92/sys/os/glnxa64:/matlabruntime/v92/sys/opengl/lib/glnxa64

