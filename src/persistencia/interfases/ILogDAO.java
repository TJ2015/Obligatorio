package persistencia.interfases;

import java.util.List;

import javax.ejb.Local;

import dominio.LogEntry;

@Local
public interface ILogDAO {
	
	public void persistirLogEntry(LogEntry le, String tenant);
	public void eliminarLogEntry(LogEntry le, String tenant);
	public LogEntry getLogEntry(long id, String tenant);
	public List<LogEntry> getLogEntryList(String tenant, int inicio, int fin);
	
}
