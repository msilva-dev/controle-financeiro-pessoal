# Histórico de Versões (Changelog)

Todo o histórico de mudanças notáveis neste projeto será documentado neste arquivo.

## [0.1.0] - 2025-10-17

### Adicionado (Added)
- Implementação do Core de Domínio com as entidades `Usuario`, `Conta` e `Transacao`.
- Suíte de testes unitários completa para toda a camada de domínio, atingindo ~99% de cobertura de linha e 100% de cobertura de decisão (branch).
- Arquitetura de `Conta` flexível com Interface, Classe Abstrata e implementações (`ContaBancaria`, `CarteiraFisica`).
- Padrões de design como Builder (para testes) e Static Factory Method.
- Validação de dados robusta em todas as entidades.
- Integração com JaCoCo e Codecov para monitoramento de cobertura de testes.
- Workflow de Integração Contínua (CI) com GitHub Actions para validação automática de builds e testes.