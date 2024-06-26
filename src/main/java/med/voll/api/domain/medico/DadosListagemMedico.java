package med.voll.api.domain.medico;

public record DadosListagemMedico(Long id, String nome, String email, String CRM, Especialidade especialidade) {
    public DadosListagemMedico (Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
