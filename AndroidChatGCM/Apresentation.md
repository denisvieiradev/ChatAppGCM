Push Message - Nome Geral, caracteristica servidor ser a parte ativa, ele utiliza o canal de comunicação que o google deixa aberto com os devices que tenham android instalado, a segunda é o utilização do XMTP, canal leve de comunicação com a internet, que seriam o upstream e downstream .

GCM é um serviço particular

steps :
1. get a configuration file in developers.google , o package name é o name lá no AndroidManifest
2. add gradles of GCM
3. modified manifest with permission.INTERNET poder enviar Registration ID para enviar msg do servidor para os devices
    permission.GET_ACCOUNTS ( versões abaixo do android 6.0)
    WAKE.LOCK ( Permitir app continue trabalhando qnd chegar uma msg, se manter trabalhando o consumo de bateria é um problema)
    C2D_message ( as mensagens só possam ser acessadas pelo meu app)
    GCM_RECEIVER ( Permite receber mensagens, exported true para ser acessado externamente) - obter registration ID abaixo do android 4.4,

Notification.compat.builder