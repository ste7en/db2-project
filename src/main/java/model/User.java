package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserPK id;

	@Column(name="account_locked")
	private String accountLocked;

	private byte admin;

	private String alter_priv;

	private String alter_routine_priv;

	@Lob
	@Column(name="authentication_string")
	private String authenticationString;

	private byte blocked;

	private String create_priv;

	private String create_role_priv;

	private String create_routine_priv;

	private String create_tablespace_priv;

	private String create_tmp_table_priv;

	private String create_user_priv;

	private String create_view_priv;

	private String delete_priv;

	private String drop_priv;

	private String drop_role_priv;

	private String email;

	private String event_priv;

	private String execute_priv;

	private String file_priv;

	private String grant_priv;

	private String index_priv;

	private String insert_priv;

	private String lock_tables_priv;

	@Column(name="max_connections")
	private int maxConnections;

	@Column(name="max_questions")
	private int maxQuestions;

	@Column(name="max_updates")
	private int maxUpdates;

	@Column(name="max_user_connections")
	private int maxUserConnections;

	private String password;

	@Column(name="password_expired")
	private String passwordExpired;

	@Column(name="password_last_changed")
	private Timestamp passwordLastChanged;

	@Column(name="password_lifetime")
	private int passwordLifetime;

	private String password_require_current;

	private int password_reuse_history;

	private int password_reuse_time;

	private String plugin;

	private String process_priv;

	private String references_priv;

	private String reload_priv;

	private String repl_client_priv;

	private String repl_slave_priv;

	private String select_priv;

	private String show_db_priv;

	private String show_view_priv;

	private String shutdown_priv;

	@Lob
	@Column(name="ssl_cipher")
	private byte[] sslCipher;

	@Column(name="ssl_type")
	private String sslType;

	private String super_priv;

	private String trigger_priv;

	private String update_priv;

	private Object user_attributes;

	@Column(name="`user-id`")
	private int user_id;

	private String username;

	@Lob
	@Column(name="x509_issuer")
	private byte[] x509Issuer;

	@Lob
	@Column(name="x509_subject")
	private byte[] x509Subject;
	
	@ElementCollection
	@CollectionTable(name = "review", joinColumns = @JoinColumn(name = "user"))
	@MapKeyJoinColumn(name = "product")
	
	@Column(name = "text")
	private Map<Product, Integer> products;
	
	
	public User() {
	}

	public UserPK getId() {
		return this.id;
	}

	public void setId(UserPK id) {
		this.id = id;
	}

	public String getAccountLocked() {
		return this.accountLocked;
	}

	public void setAccountLocked(String accountLocked) {
		this.accountLocked = accountLocked;
	}

	public byte getAdmin() {
		return this.admin;
	}

	public void setAdmin(byte admin) {
		this.admin = admin;
	}

	public String getAlter_priv() {
		return this.alter_priv;
	}

	public void setAlter_priv(String alter_priv) {
		this.alter_priv = alter_priv;
	}

	public String getAlter_routine_priv() {
		return this.alter_routine_priv;
	}

	public void setAlter_routine_priv(String alter_routine_priv) {
		this.alter_routine_priv = alter_routine_priv;
	}

	public String getAuthenticationString() {
		return this.authenticationString;
	}

	public void setAuthenticationString(String authenticationString) {
		this.authenticationString = authenticationString;
	}

	public byte getBlocked() {
		return this.blocked;
	}

	public void setBlocked(byte blocked) {
		this.blocked = blocked;
	}

	public String getCreate_priv() {
		return this.create_priv;
	}

	public void setCreate_priv(String create_priv) {
		this.create_priv = create_priv;
	}

	public String getCreate_role_priv() {
		return this.create_role_priv;
	}

	public void setCreate_role_priv(String create_role_priv) {
		this.create_role_priv = create_role_priv;
	}

	public String getCreate_routine_priv() {
		return this.create_routine_priv;
	}

	public void setCreate_routine_priv(String create_routine_priv) {
		this.create_routine_priv = create_routine_priv;
	}

	public String getCreate_tablespace_priv() {
		return this.create_tablespace_priv;
	}

	public void setCreate_tablespace_priv(String create_tablespace_priv) {
		this.create_tablespace_priv = create_tablespace_priv;
	}

	public String getCreate_tmp_table_priv() {
		return this.create_tmp_table_priv;
	}

	public void setCreate_tmp_table_priv(String create_tmp_table_priv) {
		this.create_tmp_table_priv = create_tmp_table_priv;
	}

	public String getCreate_user_priv() {
		return this.create_user_priv;
	}

	public void setCreate_user_priv(String create_user_priv) {
		this.create_user_priv = create_user_priv;
	}

	public String getCreate_view_priv() {
		return this.create_view_priv;
	}

	public void setCreate_view_priv(String create_view_priv) {
		this.create_view_priv = create_view_priv;
	}

	public String getDelete_priv() {
		return this.delete_priv;
	}

	public void setDelete_priv(String delete_priv) {
		this.delete_priv = delete_priv;
	}

	public String getDrop_priv() {
		return this.drop_priv;
	}

	public void setDrop_priv(String drop_priv) {
		this.drop_priv = drop_priv;
	}

	public String getDrop_role_priv() {
		return this.drop_role_priv;
	}

	public void setDrop_role_priv(String drop_role_priv) {
		this.drop_role_priv = drop_role_priv;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEvent_priv() {
		return this.event_priv;
	}

	public void setEvent_priv(String event_priv) {
		this.event_priv = event_priv;
	}

	public String getExecute_priv() {
		return this.execute_priv;
	}

	public void setExecute_priv(String execute_priv) {
		this.execute_priv = execute_priv;
	}

	public String getFile_priv() {
		return this.file_priv;
	}

	public void setFile_priv(String file_priv) {
		this.file_priv = file_priv;
	}

	public String getGrant_priv() {
		return this.grant_priv;
	}

	public void setGrant_priv(String grant_priv) {
		this.grant_priv = grant_priv;
	}

	public String getIndex_priv() {
		return this.index_priv;
	}

	public void setIndex_priv(String index_priv) {
		this.index_priv = index_priv;
	}

	public String getInsert_priv() {
		return this.insert_priv;
	}

	public void setInsert_priv(String insert_priv) {
		this.insert_priv = insert_priv;
	}

	public String getLock_tables_priv() {
		return this.lock_tables_priv;
	}

	public void setLock_tables_priv(String lock_tables_priv) {
		this.lock_tables_priv = lock_tables_priv;
	}

	public int getMaxConnections() {
		return this.maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public int getMaxQuestions() {
		return this.maxQuestions;
	}

	public void setMaxQuestions(int maxQuestions) {
		this.maxQuestions = maxQuestions;
	}

	public int getMaxUpdates() {
		return this.maxUpdates;
	}

	public void setMaxUpdates(int maxUpdates) {
		this.maxUpdates = maxUpdates;
	}

	public int getMaxUserConnections() {
		return this.maxUserConnections;
	}

	public void setMaxUserConnections(int maxUserConnections) {
		this.maxUserConnections = maxUserConnections;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordExpired() {
		return this.passwordExpired;
	}

	public void setPasswordExpired(String passwordExpired) {
		this.passwordExpired = passwordExpired;
	}

	public Timestamp getPasswordLastChanged() {
		return this.passwordLastChanged;
	}

	public void setPasswordLastChanged(Timestamp passwordLastChanged) {
		this.passwordLastChanged = passwordLastChanged;
	}

	public int getPasswordLifetime() {
		return this.passwordLifetime;
	}

	public void setPasswordLifetime(int passwordLifetime) {
		this.passwordLifetime = passwordLifetime;
	}

	public String getPassword_require_current() {
		return this.password_require_current;
	}

	public void setPassword_require_current(String password_require_current) {
		this.password_require_current = password_require_current;
	}

	public int getPassword_reuse_history() {
		return this.password_reuse_history;
	}

	public void setPassword_reuse_history(int password_reuse_history) {
		this.password_reuse_history = password_reuse_history;
	}

	public int getPassword_reuse_time() {
		return this.password_reuse_time;
	}

	public void setPassword_reuse_time(int password_reuse_time) {
		this.password_reuse_time = password_reuse_time;
	}

	public String getPlugin() {
		return this.plugin;
	}

	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}

	public String getProcess_priv() {
		return this.process_priv;
	}

	public void setProcess_priv(String process_priv) {
		this.process_priv = process_priv;
	}

	public String getReferences_priv() {
		return this.references_priv;
	}

	public void setReferences_priv(String references_priv) {
		this.references_priv = references_priv;
	}

	public String getReload_priv() {
		return this.reload_priv;
	}

	public void setReload_priv(String reload_priv) {
		this.reload_priv = reload_priv;
	}

	public String getRepl_client_priv() {
		return this.repl_client_priv;
	}

	public void setRepl_client_priv(String repl_client_priv) {
		this.repl_client_priv = repl_client_priv;
	}

	public String getRepl_slave_priv() {
		return this.repl_slave_priv;
	}

	public void setRepl_slave_priv(String repl_slave_priv) {
		this.repl_slave_priv = repl_slave_priv;
	}

	public String getSelect_priv() {
		return this.select_priv;
	}

	public void setSelect_priv(String select_priv) {
		this.select_priv = select_priv;
	}

	public String getShow_db_priv() {
		return this.show_db_priv;
	}

	public void setShow_db_priv(String show_db_priv) {
		this.show_db_priv = show_db_priv;
	}

	public String getShow_view_priv() {
		return this.show_view_priv;
	}

	public void setShow_view_priv(String show_view_priv) {
		this.show_view_priv = show_view_priv;
	}

	public String getShutdown_priv() {
		return this.shutdown_priv;
	}

	public void setShutdown_priv(String shutdown_priv) {
		this.shutdown_priv = shutdown_priv;
	}

	public byte[] getSslCipher() {
		return this.sslCipher;
	}

	public void setSslCipher(byte[] sslCipher) {
		this.sslCipher = sslCipher;
	}

	public String getSslType() {
		return this.sslType;
	}

	public void setSslType(String sslType) {
		this.sslType = sslType;
	}

	public String getSuper_priv() {
		return this.super_priv;
	}

	public void setSuper_priv(String super_priv) {
		this.super_priv = super_priv;
	}

	public String getTrigger_priv() {
		return this.trigger_priv;
	}

	public void setTrigger_priv(String trigger_priv) {
		this.trigger_priv = trigger_priv;
	}

	public String getUpdate_priv() {
		return this.update_priv;
	}

	public void setUpdate_priv(String update_priv) {
		this.update_priv = update_priv;
	}

	public Object getUser_attributes() {
		return this.user_attributes;
	}

	public void setUser_attributes(Object user_attributes) {
		this.user_attributes = user_attributes;
	}

	public int getUser_id() {
		return this.user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getX509Issuer() {
		return this.x509Issuer;
	}

	public void setX509Issuer(byte[] x509Issuer) {
		this.x509Issuer = x509Issuer;
	}

	public byte[] getX509Subject() {
		return this.x509Subject;
	}

	public void setX509Subject(byte[] x509Subject) {
		this.x509Subject = x509Subject;
	}

}