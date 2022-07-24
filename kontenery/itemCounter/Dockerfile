FROM golang:1.18-alpine AS builder 

WORKDIR /app 
COPY . .
RUN go mod download 
RUN go build -o /app/item-counter

FROM golang:1.18-alpine 
WORKDIR /app
COPY --from=builder /app/item-counter /app  
EXPOSE 9090
RUN addgroup --system secureusers && adduser cuser -S system -G secureusers
USER cuser 
CMD [ "/app/item-counter" ]
