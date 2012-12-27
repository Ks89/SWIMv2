package test;

import javax.ejb.Remote;

@Remote
public interface TestUtilsRemote {

	public void svuotaDatabase();

	public void svuotaTabellaDatabase(String nomeTabellaEntityBeanDaSvuotare);
	
}