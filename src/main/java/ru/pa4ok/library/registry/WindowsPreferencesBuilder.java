package ru.pa4ok.library.registry;

import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;


/**
 * Preferences builder to hack {@link Preferences} constructor and gain access
 * to HKCR, HKU and HKCC.
 * 
 * @author Yunqi Ouyang (oyyq99999)
 */
public class WindowsPreferencesBuilder {

	private static final Logger LOG = Logger.getLogger(WindowsPreferencesBuilder.class.getName());

	private static final byte[] WINDOWS_ROOT_PATH = stringToByteArray("");
	public static final int HKCR_VALUE = 0x80000000;
	public static final int HKCU_VALUE = 0x80000001;
	public static final int HKLM_VALUE = 0x80000002;
	public static final int HKU_VALUE = 0x80000003;
	public static final int HKCC_VALUE = 0x80000005;
	private static Preferences hkcr = null;
	private static Preferences hkcu = Preferences.userRoot();
	private static Preferences hklm = Preferences.systemRoot();
	private static Preferences hku = null;
	private static Preferences hkcc = null;

	static {
		Class<? extends Preferences> clazz = Preferences.userRoot().getClass();
		Constructor<? extends Preferences> constructor;
		try {
			constructor = clazz.getDeclaredConstructor(int.class, byte[].class);
			constructor.setAccessible(true);
			hkcr = constructor.newInstance(HKCR_VALUE, WINDOWS_ROOT_PATH);
			hku = constructor.newInstance(HKU_VALUE, WINDOWS_ROOT_PATH);
			hkcc = constructor.newInstance(HKCC_VALUE, WINDOWS_ROOT_PATH);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Cannot instantiate preferences", e);
		}
	}

	public static Preferences getHKCR() {
		return hkcr;
	}

	public static Preferences getHKCU() {
		return hkcu;
	}

	public static Preferences getHKLM() {
		return hklm;
	}

	public static Preferences getHKU() {
		return hku;
	}

	public static Preferences getHKCC() {
		return hkcc;
	}

	/**
	 * Returns this java string as a null-terminated byte array
	 */
	private static byte[] stringToByteArray(String str) {
		byte[] result = new byte[str.length() + 1];
		for (int i = 0; i < str.length(); i++) {
			result[i] = (byte) str.charAt(i);
		}
		result[str.length()] = 0;
		return result;
	}
}