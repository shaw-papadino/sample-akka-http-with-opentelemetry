## akka-http-with-opentelemetry
AOpenTelemetryのAuto Instrument機能を使って, Akka-HTTPアプリケーションをトレーシングしたサンプルプロジェクトです。

## 動作確認
1. docker composeで以下のコンテナを起動する
   - opentelemetry collector
   - jaeger
   - zipkin
```
cd docker
docker compose up
```

2. Akka-HTTPアプリケーションを起動する
```
sbt run
```
3. 複数回以下のコマンドでリクエストを送信する。
```
curl localhost:8080/users
```