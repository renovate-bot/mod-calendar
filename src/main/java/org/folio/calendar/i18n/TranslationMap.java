package org.folio.calendar.i18n;

import java.util.Locale;
import java.util.Map;
import javax.annotation.CheckForNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class TranslationMap {

  @Getter
  protected final Locale locale;

  @Getter
  protected final TranslationMatchQuality quality;

  @Getter
  protected final TranslationFile file;

  protected final Map<String, String> patterns;

  @CheckForNull
  protected final TranslationMap fallback;

  /**
   * Create a TranslationMap for a given locale based off of a file with contents and a given fallback.
   * @param locale the associated locale
   * @param file the {@link TranslationFile TranslationFile} to infer quality from
   * @param patterns the patterns to use
   * @param fallback the TranslationMap to search if a given translation cannot be found
   */
  protected TranslationMap(
    Locale locale,
    TranslationFile file,
    Map<String, String> patterns,
    @CheckForNull TranslationMap fallback
  ) {
    this.locale = locale;
    this.quality = TranslationMatchQuality.getQuality(locale, file);
    this.file = file;
    this.patterns = patterns;
    this.fallback = fallback;
  }

  /**
   * Create a TranslationMap for a given locale based off of a file with a given fallback.
   * @param locale the associated locale
   * @param file the {@link TranslationFile TranslationFile} to read from
   * @param fallback the TranslationMap to search if a given translation cannot be found
   */
  public TranslationMap(
    Locale locale,
    TranslationFile file,
    @CheckForNull TranslationMap fallback
  ) {
    this(locale, file, file.getMap(), fallback);
  }

  /**
   * Create a <em>default</em> TranslationMap for a given locale based off of a file.
   * This constructor should only be used for default translation maps; all others
   * should use the other constructor and provide a fallback.
   * @param locale the associated locale
   * @param file the {@link TranslationFile TranslationFile} to read from
   */
  public TranslationMap(Locale locale, TranslationFile file) {
    this(locale, file, null);
  }

  public TranslationMap withLocale(Locale newLocale) {
    return new TranslationMap(newLocale, this.file, this.patterns, this.fallback);
  }

  /**
   * Get the ICU format string associated with a given key for this translation
   * @param key the key to lookup
   * @return one of the following, with this precedence:
   * <ol>
   *   <li>the ICU format string for this locale, if it is known</li>
   *   <li>the ICU format string for a locale with a worse {@code TranslationMatchQuality},
   *     such as one without a specific country, recursing through the {@code fallback}</li>
   *   <li>the ICU format string for the server's default locale</li>
   *   <li>the provided key</li>
   * </ol>
   */
  public String get(String key) {
    if (this.patterns.containsKey(key)) {
      return this.patterns.get(key);
    } else if (this.fallback != null) {
      return this.fallback.get(key);
    } else {
      return key;
    }
  }
}
