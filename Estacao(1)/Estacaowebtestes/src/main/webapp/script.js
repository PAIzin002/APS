document.addEventListener('DOMContentLoaded', () => {
    lucide.createIcons();

    // --- Elementos principais ---
    const barraLateral = document.getElementById('barra-lateral');
    const botaoMenu = document.getElementById('botao-menu');
    const sobreposicao = document.getElementById('sobreposicao');
    const tituloPagina = document.getElementById('titulo-pagina');
    const containerChuva = document.getElementById('container-chuva');
    const abas = document.querySelectorAll('.conteudo-aba');
    const links = document.querySelectorAll('.link-navegacao');
    const ctx = document.getElementById('graficoSensor').getContext('2d');
    const filtroData = document.getElementById('filtro-data');
    const botaoFiltrar = document.getElementById('botao-filtrar');

    let leiturasGlobais = [];
    let filtroAtivo = ''; // armazenar a data filtrada
    let meuGrafico; // variável global para o chart
    let filtroGrafico = null; // null, 'temperatura' ou 'umidade'

    // --- Menu Mobile ---
    function alternarMenu() {
        barraLateral.classList.toggle('is-open');
        sobreposicao.classList.toggle('oculto');
    }
    botaoMenu.addEventListener('click', alternarMenu);
    sobreposicao.addEventListener('click', alternarMenu);

    // --- Navegação entre abas ---
    links.forEach(link => {
        link.addEventListener('click', e => {
            e.preventDefault();
            const abaAlvo = link.dataset.aba;

            abas.forEach(aba => {
                aba.classList.remove('ativo');
                aba.classList.add('oculto');
            });

            const novaAba = document.getElementById(abaAlvo);
            novaAba.classList.remove('oculto');
            setTimeout(() => novaAba.classList.add('ativo'), 10);

            tituloPagina.textContent = link.textContent.trim();
            links.forEach(l => l.classList.remove('ativo'));
            link.classList.add('ativo');

            if (window.innerWidth < 1024 && barraLateral.classList.contains('is-open')) {
                alternarMenu();
            }
        });
    });

    // --- Animação de chuva ---
    function definirChuva(estaChovendo) {
        const corpo = document.body;
        const valorChuva = document.getElementById('valor-chuva');
        const wrapperIconeChuva = document.getElementById('wrapper-icone-chuva');
        containerChuva.innerHTML = '';

        if (estaChovendo) {
            corpo.classList.add('chovendo');
            valorChuva.textContent = 'Chovendo';
            wrapperIconeChuva.innerHTML = '<i data-lucide="cloud-rain"></i>';
            lucide.createIcons();

            const quantidade = 100;
            let gotas = '';
            for (let i = 0; i < quantidade; i++) {
                const posicaoEsquerda = Math.random() * 100;
                const duracao = Math.random() * 0.5 + 0.5;
                const atraso = Math.random() * 5;
                const largura = 1 + Math.random() * 2;
                const opacidade = 0.3 + Math.random() * 0.7;

                gotas += `<div class="raindrop" style="
                    left:${posicaoEsquerda}%;
                    width:${largura}px;
                    opacity:${opacidade};
                    animation-duration:${duracao}s;
                    animation-delay:${atraso}s;
                "></div>`;
            }
            containerChuva.innerHTML = gotas;
        } else {
            corpo.classList.remove('chovendo');
            valorChuva.textContent = 'Sem Chuva';
            wrapperIconeChuva.innerHTML = '<i data-lucide="cloud-off"></i>';
        }

        lucide.createIcons();
    }

    // --- Formata datas para yyyy-mm-dd ---
    function formatarDataParaInput(dataHora) {
        const d = new Date(dataHora.replace(/\u202F/g, ' ').trim());
        if (isNaN(d)) return '';
        const ano = d.getFullYear();
        const mes = String(d.getMonth() + 1).padStart(2, '0');
        const dia = String(d.getDate()).padStart(2, '0');
        return `${ano}-${mes}-${dia}`;
    }

    // --- Atualiza painel principal ---
    function mostrarDadosPainel(dados) {
        document.getElementById('valor-temperatura').textContent = dados.temperatura;
        document.getElementById('valor-umidade').textContent = dados.umidade;
        document.getElementById('ultima-atualizacao').textContent = new Date().toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit', second: '2-digit' });
    }

    // --- Preenche histórico ---
    function popularHistorico(dados) {
        const corpoTabela = document.getElementById('corpo-tabela-historico');
        corpoTabela.innerHTML = '';

        dados.forEach(r => {
            const dataHoraTexto = r.dataHora.replace(/\u202F/g, ' ').trim();
            const dataObj = new Date(dataHoraTexto);

            const dataFormatada = !isNaN(dataObj.getTime())
                ? dataObj.toLocaleDateString('pt-BR', { day: '2-digit', month: '2-digit', year: 'numeric' })
                : dataHoraTexto;

            const horaFormatada = !isNaN(dataObj.getTime())
                ? dataObj.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit', second: '2-digit' })
                : '--:--';

            corpoTabela.innerHTML += 
                `<tr>
                    <td>${dataFormatada}</td>
                    <td>${horaFormatada}</td>
                    <td>${r.temperatura}</td>
                    <td>${r.umidade}</td>
                </tr>`;
        });
    }

    // --- Gráfico com eixo fixo 00h-22h e alternância ---
    function gerarGrafico(leituras) {
        // Horas fixas: 00h, 02h, 04h ... 22h
        const horas = [];
        for (let h = 0; h < 24; h += 2) {
            horas.push(h.toString().padStart(2, '0') + ':00');
        }

        const temperaturas = new Array(horas.length).fill(null);
        const umidades = new Array(horas.length).fill(null);

        leituras.forEach(dado => {
            const data = new Date(dado.dataHora.replace(/\u202F/g, ' ').trim());
            if (!isNaN(data.getTime())) {
                const hora = data.getHours();
                const index = Math.floor(hora / 2);
                temperaturas[index] = dado.temperatura;
                umidades[index] = dado.umidade;
            }
        });

        if (meuGrafico) meuGrafico.destroy();

        meuGrafico = new Chart(ctx, {
            type: 'line',
            data: {
                labels: horas,
                datasets: [
                    {
                        label: 'Temperatura (°C)',
                        data: temperaturas,
                        borderColor: 'rgb(255, 99, 132)',
                        backgroundColor: 'rgba(255, 99, 132, 0.3)',
                        fill: false,
                        tension: 0.3,
                        hidden: filtroGrafico === 'umidade'
                    },
                    {
                        label: 'Umidade (%)',
                        data: umidades,
                        borderColor: 'rgb(54, 162, 235)',
                        backgroundColor: 'rgba(54, 162, 235, 0.3)',
                        fill: false,
                        tension: 0.3,
                        hidden: filtroGrafico === 'temperatura'
                    }
                ]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                        onClick: (e, legendaItem, legenda) => {
                            const index = legendaItem.datasetIndex;
                            if ((filtroGrafico === 'temperatura' && index === 0) ||
                                (filtroGrafico === 'umidade' && index === 1)) {
                                filtroGrafico = null;
                            } else {
                                filtroGrafico = index === 0 ? 'temperatura' : 'umidade';
                            }
                            legenda.chart.data.datasets.forEach((ds, i) => {
                                if (filtroGrafico === null) {
                                    ds.hidden = false;
                                } else {
                                    ds.hidden = (i === 0 && filtroGrafico === 'umidade') ||
                                                (i === 1 && filtroGrafico === 'temperatura');
                                }
                            });
                            legenda.chart.update();
                        }
                    },
                    title: {
                        display: true,
                        text: 'Gráfico das Variáveis Ambientais'
                    }
                },
                scales: {
                    x: { title: { display: true, text: 'Horário' } },
                    y: { beginAtZero: false, title: { display: true, text: 'Valores (°C / %)' } }
                }
            }
        });
    }

    // --- Filtro de Histórico ---
    function aplicarFiltro(valorFiltro) {
        filtroAtivo = valorFiltro;
        if (!valorFiltro) {
            filtroAtivo = '';
            popularHistorico(leiturasGlobais.slice().reverse());
            return;
        }

        const filtrados = leiturasGlobais.filter(item => {
            return formatarDataParaInput(item.dataHora) === filtroAtivo;
        });

        popularHistorico(filtrados.slice().reverse());
    }

    botaoFiltrar.addEventListener('click', () => aplicarFiltro(filtroData.value));
    filtroData.addEventListener('input', () => {
        if (!filtroData.value) {
            aplicarFiltro('');
        }
    });

    // --- Atualização de dados ---
    async function carregarDados() {
        try {
            const resposta = await fetch('http://localhost:8080/Estacaowebtestes/grafico');
            if (!resposta.ok) throw new Error('Erro ao buscar dados do servidor');

            const leituras = await resposta.json();
            if (!leituras || leituras.length === 0) return;

            leiturasGlobais = leituras;

            const ultima = leituras[leituras.length - 1];
            const dadosAtuais = {
                temperatura: `${ultima.temperatura}°C`,
                umidade: `${ultima.umidade}%`,
                estaChovendo: ultima.chuva
            };

            mostrarDadosPainel(dadosAtuais);
            definirChuva(dadosAtuais.estaChovendo);

            if (filtroAtivo) {
                filtroData.value = filtroAtivo;
                const filtrados = leiturasGlobais.filter(item => {
                    return formatarDataParaInput(item.dataHora) === filtroAtivo;
                });
                popularHistorico(filtrados.slice().reverse());
            } else {
                filtroData.value = '';
                popularHistorico(leiturasGlobais.slice().reverse());
            }

            gerarGrafico(leiturasGlobais);

        } catch (erro) {
            console.error('Falha ao carregar dados:', erro);
        }
    }

    // --- Inicialização ---
    carregarDados();
    setInterval(carregarDados, 10000);
});
