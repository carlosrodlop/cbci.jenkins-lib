/**
 * toAlphanumeric converts text to strictly alphanumeric form.
 * Example: a_B-c.1 -> abc1
 */
def call(Map opts = [:]) {
    opts.text.toLowerCase().replaceAll("[^a-z0-9]", "")
}
