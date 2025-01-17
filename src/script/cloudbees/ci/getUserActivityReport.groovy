import org.jenkinsci.plugins.useractivity.*
import org.jenkinsci.plugins.useractivity.dao.JsonUserActivityDao
import org.jenkinsci.plugins.useractivity.dao.UserActivityDao
import org.jenkinsci.plugins.useractivity.migrations.JsonV1ToV2Migration

def getUserActivityDao() {
    return ExtensionList.lookupSingleton(UserActivityPlugin.class).getUserActivityDao()
}

def generateAuthAndScmAccessReport() {
    UserActivityDao userActivityDao = getUserActivityDao()
    def authAccessReport = userActivityDao.generateAuthAccessReport()
    def scmAccessReport = userActivityDao.generateScmAccessReport()
    
    println """Auth and SCM Access Reports:
Auth Access:
${authAccessReport}

SCM Access:
${scmAccessReport}
"""
}

def generateUserActivitySummaryReport(String days, String timeUnit) {
    UserActivityDao userActivityDao = getUserActivityDao()
    UserActivitySummary activitySummary = userActivityDao.generateUserActivitySummary(days, timeUnit)
    
    def users = activitySummary.getUsers().size()
    def uniqueUsers = activitySummary.getUniqueUsers().size()
    
    println """User Activity Summary:
Total Users: ${users}
Unique Users: ${uniqueUsers}
"""
}

// Example usage
generateAuthAndScmAccessReport()
generateUserActivitySummaryReport("100", "m")

null