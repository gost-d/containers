FROM python:3.8-slim-buster
WORKDIR /app 
COPY . . 
RUN pip3 install -r requirements.txt 
RUN addgroup --system secureusers && useradd --system --group secureusers ruser
USER ruser 
CMD [ "python3", "-u", "main.py"]
