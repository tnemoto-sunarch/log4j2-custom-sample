## なんとなく作ったログテスト用のLog4j2
- Log4j2をカスタムして、ログのテストに使用する

### 見るべきところ
- jp.co.sunarch.sample.logger.CoustomAppender
    - 拾いたいログだけ拾う用のカスタムアペンダ
- jp.co.sunarch.sample.service.SampleServiceTest
    - CoustomAppenderを使ってログのテスト
- jp.co.sunarch.sample.service.SampleServiceTest2
    - CoustomAppenderではなく、もっと単純なログ確認

## 免責事項
ただのサンプルなので自己責任で。
何が起きても当方は責任を持ちません。
