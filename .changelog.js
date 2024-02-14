module.exports = {
    preset: "conventionalcommits",
    parserOpts: {
        mergePattern: /^Merge .+$/,
        headerPattern: /^(?:(\w*)?(?:\(([\w\$\.\-\* ]*)\))?\: )?(.*)$/,
    },
    writerOpts: {
        finalizeContext: (context, options, commits, keyCommit) => {
            // skip merge commits
            for (const commitGroup of context.commitGroups) {
                commitGroup.commits = commitGroup.commits.filter((commit) => commit.merge == null)
            }

            context.commitGroups = context.commitGroups.filter((group) => group.commits.length > 0)

            return context
        }
    },
    presetConfig: {
        types: [
            {
                type: "feat",
                section: "Features",
            },
            {
                type: "patch",
                section: "Changes",
            },
            {
                type: "fix",
                section: "Bug Fixes",
            },
            {
                type: "style",
                section: "Visuals",
                hidden: false
            },
        ],
        releaseCommitMessageFormat: "chore(release): {{currentTag}} [skip ci]",
    }
}