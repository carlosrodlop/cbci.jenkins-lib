MKFILE_DIR      := $(abspath $(dir $(lastword $(MAKEFILE_LIST))))
PARENT_MKFILE   := $(PARENT_REPO)/Makefile

include $(PARENT_MKFILE)
