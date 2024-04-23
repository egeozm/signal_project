{\rtf1\ansi\ansicpg1252\cocoartf2761
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fnil\fcharset0 .SFNS-Regular;\f1\fnil\fcharset0 .SFNS-Bold;\f2\fnil\fcharset0 HelveticaNeue;
}
{\colortbl;\red255\green255\blue255;\red0\green0\blue0;\red255\green255\blue255;\red233\green233\blue231;
}
{\*\expandedcolortbl;;\cssrgb\c0\c0\c0;\cssrgb\c100000\c100000\c100000\c0;\cssrgb\c92955\c92954\c92490;
}
{\*\listtable{\list\listtemplateid1\listhybrid{\listlevel\levelnfc23\levelnfcn23\leveljc0\leveljcn0\levelfollow0\levelstartat1\levelspace360\levelindent0{\*\levelmarker \{disc\}}{\leveltext\leveltemplateid1\'01\uc0\u8226 ;}{\levelnumbers;}\fi-360\li720\lin720 }{\listname ;}\listid1}
{\list\listtemplateid2\listhybrid{\listlevel\levelnfc23\levelnfcn23\leveljc0\leveljcn0\levelfollow0\levelstartat1\levelspace360\levelindent0{\*\levelmarker \{disc\}}{\leveltext\leveltemplateid101\'01\uc0\u8226 ;}{\levelnumbers;}\fi-360\li720\lin720 }{\listname ;}\listid2}}
{\*\listoverridetable{\listoverride\listid1\listoverridecount0\ls1}{\listoverride\listid2\listoverridecount0\ls2}}
\paperw11900\paperh16840\margl1440\margr1440\vieww14980\viewh8400\viewkind0
\deftab720
\pard\pardeftab720\sa120\partightenfactor0

\f0\fs30 \cf2 \cb3 \expnd0\expndtw0\kerning0
UML Diagram Documentation for Patient Health Monitoring System\
Design Overview:\
The UML class diagram illustrates a modular health monitoring system comprising several data generators, each adhering to the \'93
\f1\b PatientDataGenerator
\f0\b0 \'94 interface. This interface ensures consistent data generation, for simulating various patient health metrics such as alerts, blood levels, blood pressure, blood saturation, and ECG.\
Rational: \
\pard\tx220\tx720\pardeftab720\li720\fi-720\sa120\partightenfactor0
\ls1\ilvl0\cf2 \kerning1\expnd0\expndtw0 {\listtext	\uc0\u8226 	}Modularity: The interface-driven approach ensures system extensibility, allowing new health data types to integrate seamlessly.\
{\listtext	\uc0\u8226 	}Single Responsibility: Individual classes for alerts, blood levels, and other metrics simplify maintenance and upload the Single Responsibility Principle.\
{\listtext	\uc0\u8226 	}Randomisation: A \'93
\f1\b Random
\f0\b0 \'94 class instance in each generator mimics the unpredictability of patient health metrics, providing a unified method for data variation.\
{\listtext	\uc0\u8226 	}Encapsulation: Internal state management within each class secures data integrity and hides implementation specifies.\expnd0\expndtw0\kerning0
\
\pard\tx220\tx720\pardeftab720\li720\fi-720\sa120\partightenfactor0
\cf2 \
Assumption: \
\pard\tx220\tx720\pardeftab720\li720\fi-720\sa120\partightenfactor0
\ls2\ilvl0\cf2 \kerning1\expnd0\expndtw0 {\listtext	\uc0\u8226 	}The design assumes patient IDs are unique integers, facilitating straightforward data corporation.\
{\listtext	\uc0\u8226 	}Health metric data storage is abstracted, focusing on system design rather than data persistence mechanisms.\
{\listtext	\uc0\u8226 	}The \'93
\f1\b OutputStrategy
\f0\b0 \'94 is expecting to represent various data handling strategies, adaptable to the system\'92s output needs.\
\pard\tx720\pardeftab720\sa120\partightenfactor0
\cf2 \expnd0\expndtw0\kerning0
\
Conclusion: \
\pard\pardeftab560\slleading20\partightenfactor0
\cf0 \cb1 \kerning1\expnd0\expndtw0 The design of the health monitoring system is aimed at flexibility. The UML diagram and the accompanying code reflect a system that can evolve to include new data types and handle complex patient data scenarios while maintaining a clear and manageable codebase
\f2 .
\f0 \cf2 \cb3 \expnd0\expndtw0\kerning0
\
}