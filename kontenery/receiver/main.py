
import redis
import pika
import json
import sys, os
import logging

QUEUE = os.getenv('QUEUE','java_queue')
RABIT_HOST = os.getenv('RABIT_HOST', 'localhost')
REDIS_PORT = os.getenv('REDIS_PORT',6379)
REDIS_HOST = os.getenv('REDIS_HOST','localhost')
USER = os.getenv('USER', 'user')
PASSWORD = os.getenv('PASSWORD','user')
RABBIT_PORT = os.getenv('RABBIT_PORT',5672)

credentials = pika.PlainCredentials(USER, PASSWORD)
redis_client = redis.Redis(host=REDIS_HOST,port=REDIS_PORT,db=0)
rabbit_connection = pika.BlockingConnection(pika.ConnectionParameters(host=RABIT_HOST,port=RABBIT_PORT,virtual_host='/',credentials=credentials))
log = logging.getLogger("my-logger")

def callback(ch, method, properties, body):
    received_json = json.loads(body)
    redis_client.set(json.loads(body)["userId"], body)
    redis_client.expire(received_json["userId"], 60)
    log.info(received_json)
    print(received_json)


def main():
    channel = rabbit_connection.channel()
    channel.queue_declare(queue=QUEUE,durable=True)
    channel.basic_consume(queue=QUEUE, auto_ack=True, on_message_callback=callback)
    log.info(' [*] Waiting for messages. To exit press CTRL+C')
    channel.start_consuming()


if __name__ == '__main__':
        try:
            main()
        except KeyboardInterrupt:
            print('Interrupted')
            try:
                sys.exit(0)
            except SystemExit:
                os._exit(0)
