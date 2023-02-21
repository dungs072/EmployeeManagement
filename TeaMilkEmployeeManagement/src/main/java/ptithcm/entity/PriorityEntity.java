package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "QUYEN")
public class PriorityEntity {
	
	@Id
	private String MAQUYEN;
	
	@Column(name ="MOTA")
	private String MOTA;
}
